package nwoolcan.model.database;

import com.google.gson.reflect.TypeToken;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluationBuilder;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.production.batch.review.Evaluation;
import nwoolcan.model.brewery.production.batch.review.EvaluationFactory;
import nwoolcan.model.brewery.production.batch.review.types.BJCPBatchEvaluationType;
import nwoolcan.model.brewery.production.batch.review.types.BJCPBatchEvaluationType.BJCPCategories;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
     * Initialized shared objects.
     */
    @Before
    public void setup() {
        db = new DatabaseJsonImpl();

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
        final Record deserializedRecord = db.loadRecord(db.save(this.record));
        Assert.assertEquals(deserializedRecord.getAction(), this.record.getAction());
        Assert.assertTrue("Dates does not match", Math.abs(deserializedRecord.getDate().getTime() - this.record.getDate().getTime()) < 1000);
        Assert.assertEquals(deserializedRecord.getQuantity().getUnitOfMeasure(), this.record.getQuantity().getUnitOfMeasure());
        Assert.assertEquals(deserializedRecord.getQuantity().getValue(), this.record.getQuantity().getValue(), this.DOUBLE_DELTA);
    }

    /**
     * Test if a BatchEvaluation can be correctly serialized and then recreated.
     */
    @Test
    public void loadBatchEvaluation() {
        Assert.assertEquals(db.loadEvaluation(db.save(this.evaluation)), this.evaluation);
    }

    /**
     * Test (de)serialization of Optional of a simple type like Integer.
     */
    @Test
    public void optionalSimpleType() {
        final Optional<Integer> optIntValue = Optional.of(10);
        final Optional<Integer> optIntEmpty = Optional.empty();

        Assert.assertEquals(this.db.deserialize(this.db.serialize(optIntValue), new TypeToken<Optional<Integer>>() { }), optIntValue);
        Assert.assertEquals(this.db.deserialize(this.db.serialize(optIntEmpty), new TypeToken<Optional<Integer>>() { }), optIntEmpty);
    }

    /**
     * Test (de)serialization of Optional of a complex type (with attributes).
     */
    @Test
    public void optionalComplexType() {
        final Optional<Quantity> optQuantValue = Optional.of(this.quantity);
        final Optional<Quantity> optQuantEmpty = Optional.empty();

        Assert.assertEquals(this.db.deserialize(this.db.serialize(optQuantValue), new TypeToken<Optional<Quantity>>() { }), optQuantValue);
        Assert.assertEquals(this.db.deserialize(this.db.serialize(optQuantEmpty), new TypeToken<Optional<Quantity>>() { }), optQuantEmpty);
    }

    /**
     * Test (de)serialization of Result of a complex type (with attributes).
     */
    @Test
    public void resultComplexType() {
        final Result<Quantity> resQuantValue = Result.of(this.quantity);
        final Result<Quantity> resQuantError = Result.error(new Exception());

        Assert.assertEquals(this.db.deserialize(this.db.serialize(resQuantValue), new TypeToken<Result<Quantity>>() { }), resQuantValue);
        final Result<Quantity> resQuantErrorDeserialized = this.db.deserialize(this.db.serialize(resQuantError), new TypeToken<Result<Quantity>>() { });
        Assert.assertEquals(resQuantErrorDeserialized.isError(), resQuantError.isError());
        Assert.assertEquals(resQuantErrorDeserialized.getError().getClass(), resQuantError.getError().getClass());
    }
}
