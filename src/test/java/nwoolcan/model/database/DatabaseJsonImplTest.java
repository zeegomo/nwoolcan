package nwoolcan.model.database;

import nwoolcan.model.brewery.production.batch.review.*;
import nwoolcan.model.brewery.production.batch.review.types.BJCPBatchEvaluationType;
import nwoolcan.model.brewery.production.batch.review.types.BJCPBatchEvaluationType.BJCPCategories;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tests the database functionality.
 */
public class DatabaseJsonImplTest {

    private Database db;
    private final Record record = new Record(Quantity.of(1, UnitOfMeasure.UNIT).getValue(), Record.Action.ADDING);
    private BatchEvaluation evaluation;

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
        Assert.assertEquals(deserializedRecord.getQuantity().getValue(), this.record.getQuantity().getValue(), 0.0001);
    }

    /**
     * Test if a BatchEvaluation can be correctly serialized and then recreated.
     */
    @Test
    public void loadBatchEvaluation() {
        final String json = db.save(this.evaluation);
        Logger.getAnonymousLogger().warning(json);
        final BatchEvaluation deserializedEvaluation = db.loadEvaluation(json);
        Assert.assertEquals(deserializedEvaluation, this.evaluation);
    }
}
