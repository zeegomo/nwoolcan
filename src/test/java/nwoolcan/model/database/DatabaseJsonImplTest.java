package nwoolcan.model.database;

import nwoolcan.model.brewery.production.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluationBuilder;
import nwoolcan.model.brewery.production.batch.review.types.BJCPBatchEvaluationType;
import nwoolcan.model.brewery.warehouse.Record;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

/**
 * Tests the database functionality.
 */
public class DatabaseJsonImplTest {

    private Database db;
    private final Record record = new Record(Quantity.of(1, UnitOfMeasure.Kilogram), Record.Action.ADDING);
    private BatchEvaluation evaluation;

    /**
     * Initialized shared objects.
     */
    @Before
    public void setup() {
        db = new DatabaseJsonImpl();

        final BatchEvaluationBuilder evalBuilder = new BatchEvaluationBuilder(new BJCPBatchEvaluationType());
        Result<BatchEvaluation> bjcp = evalBuilder
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.AROMA, 10)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.APPEARANCE, 3)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.FLAVOR,
                BJCPBatchEvaluationType.BJCPCategories.FLAVOR.getMaxScore())
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.MOUTHFEEL, 4)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.OVERALL_IMPRESSION, 10)
            .addReviewer("Andrea")
            .addNotes("Very good")
            .build();

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
        Assert.assertEquals(deserializedRecord.getQuantity().getValue().intValue(), this.record.getQuantity().getValue().intValue());
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
