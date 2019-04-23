package nwoolcan.model.database;

import com.google.gson.reflect.TypeToken;
import nwoolcan.model.brewery.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.batch.review.BatchEvaluationBuilder;
import nwoolcan.model.brewery.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.batch.review.Evaluation;
import nwoolcan.model.brewery.batch.review.EvaluationFactory;
import nwoolcan.model.brewery.batch.review.types.BJCPBatchEvaluationType;
import nwoolcan.model.brewery.batch.review.types.BJCPBatchEvaluationType.BJCPCategories;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tests the database functionality.
 */
public class DatabaseJsonImplTest {

    private static final double DOUBLE_DELTA = 0.0001;
    private DatabaseJsonImpl db;
    private final Record record = new Record(Quantity.of(1, UnitOfMeasure.UNIT).getValue(), Record.Action.ADDING);
    private BatchEvaluation evaluation;
    private final Quantity quantity = Quantity.of(10, UnitOfMeasure.UNIT).getValue();

    /**
     * Serializes and de-serializes the given object.
     * @param obj The object to re-serialize
     * @param type The TypeToken representing that object's type
     * @param <T> The type of the object
     * @return A copy of the object (hopefully)
     */
    private <T> T reserialize(final T obj, final TypeToken<T> type) {
        return this.db.serialize(obj)
            .flatMap(s -> this.db.deserialize(s, type))
            .getValue();
    }

    /**
     * Initialized shared objects.
     */
    @Before
    public void setup() {
        db = new DatabaseJsonImpl("");

        final BatchEvaluationType bjcpType = BatchEvaluationBuilder.getAvailableBatchEvaluationTypes()
                                                                   .getValue()
                                                                   .stream()
                                                                   .filter(s -> s.getClass().equals(BJCPBatchEvaluationType.class))
                                                                   .findAny().get();

        Set<Evaluation> evals = Stream.<Result<Evaluation>>builder()
                                      .add(EvaluationFactory.create(BJCPCategories.AROMA, 10))
                                      .add(EvaluationFactory.create(BJCPCategories.APPEARANCE, 3))
                                      .add(EvaluationFactory.create(BJCPCategories.FLAVOR, BJCPCategories.FLAVOR.getMaxScore()))
                                      .add(EvaluationFactory.create(BJCPCategories.MOUTHFEEL, 4))
                                      .add(EvaluationFactory.create(BJCPCategories.OVERALL_IMPRESSION, 10))
                                      .build()
                                      .filter(Result::isPresent)
                                      .map(Result::getValue)
                                      .collect(Collectors.toSet());


        BatchEvaluationBuilder builder = new BatchEvaluationBuilder();

        Result<BatchEvaluation> bjcp = builder.addReviewer("Andrea")
                                              .addNotes("Very good")
                                              .build(bjcpType, evals);


        this.evaluation = bjcp.getValue();
    }

    /**
     * Tests if a Record can be correctly serialized and then restored.
     */
    @Test
    public void loadRecord() {
        final Record deserializedRecord = this.reserialize(this.record, new TypeToken<Record>() { });
        Assert.assertEquals(deserializedRecord.getAction(), this.record.getAction());
        Assert.assertTrue("Dates does not match", Math.abs(deserializedRecord.getDate().getTime() - this.record.getDate().getTime()) < 1000);
        Assert.assertEquals(deserializedRecord.getQuantity().getUnitOfMeasure(), this.record.getQuantity().getUnitOfMeasure());
        Assert.assertEquals(deserializedRecord.getQuantity().getValue(), this.record.getQuantity().getValue(), DOUBLE_DELTA);
    }

    /**
     * Test if a BatchEvaluation can be correctly serialized and then recreated.
     */
    @Test
    public void loadBatchEvaluation() {
        Assert.assertEquals(this.reserialize(this.evaluation, new TypeToken<BatchEvaluation>() { }), this.evaluation);
    }

    /**
     * Test (de)serialization of Optional of a simple type like Integer.
     */
    @Test
    public void optionalSimpleType() {
        final Optional<Integer> optIntValue = Optional.of(10);
        final Optional<Integer> optIntEmpty = Optional.empty();

        Assert.assertEquals(this.reserialize(optIntValue, new TypeToken<Optional<Integer>>() { }), optIntValue);
        Assert.assertEquals(this.reserialize(optIntEmpty, new TypeToken<Optional<Integer>>() { }), optIntEmpty);
    }

    /**
     * Test (de)serialization of Optional of a complex type (with attributes).
     */
    @Test
    public void optionalComplexType() {
        final Optional<Quantity> optQuantValue = Optional.of(this.quantity);
        final Optional<Quantity> optQuantEmpty = Optional.empty();

        Assert.assertEquals(this.reserialize(optQuantValue, new TypeToken<Optional<Quantity>>() { }), optQuantValue);
        Assert.assertEquals(this.reserialize(optQuantEmpty, new TypeToken<Optional<Quantity>>() { }), optQuantEmpty);
    }

    /**
     * Test (de)serialization of Result of a complex type (with attributes).
     */
    @Test
    public void resultComplexType() {
        final Result<Quantity> resQuantValue = Result.of(this.quantity);
        final Result<Quantity> resQuantError = Result.error(new Exception());

        Assert.assertEquals(this.reserialize(resQuantValue, new TypeToken<Result<Quantity>>() { }), resQuantValue);
        final Result<Quantity> resQuantErrorDeserialized = this.reserialize(resQuantError, new TypeToken<Result<Quantity>>() { });
        Assert.assertEquals(resQuantErrorDeserialized.isError(), resQuantError.isError());
        Assert.assertEquals(resQuantErrorDeserialized.getError().getClass(), resQuantError.getError().getClass());
    }

    /**
     * Test serialization of hash maps.
     */
    @Test
    public void hashMap() {
        final Map<Quantity, Quantity> map = new HashMap<>();
        map.put(this.quantity, this.quantity);

        Assert.assertEquals(this.reserialize(map, new TypeToken<Map<Quantity, Quantity>>() { }), map);
    }
}
