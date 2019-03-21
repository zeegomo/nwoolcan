package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.utils.Quantity;

public interface ModifiableBatchInfo extends BatchInfo {
    /**
     * Set the original gravity for this batch.
     * @param og the original gravity to record.
     * @return this.
     */
    ModifiableBatchInfo setOg(Quantity og);
    /**
     * Set the final gravity for this batch.
     * @param fg the final gravity to record.
     * @return this.
     */
    ModifiableBatchInfo setFg(Quantity fg);
    /**
     * Set the srm for this batch.
     * @param srm the srm to record.
     * @return this.
     */
    ModifiableBatchInfo setSrm(Quantity srm);
    /**
     * Set the alcohol by volume for this batch.
     * @param abv the quantity to record.
     * @return this.
     */
    ModifiableBatchInfo setAbv(Quantity abv);
    /**
     * Set the bitterness for this batch.
     * @param ibu the bitterness to record.
     * @return this.
     */
    ModifiableBatchInfo setIbu(Quantity ibu);
    /**
     * Set the water measurements for this batch.
     * @param water measurements to record.
     * @return this.
     */
    ModifiableBatchInfo setWaterMeasurements(WaterMeasurement water);
    /**
     * Add an ingredient to this batch.
     * @param ingredient the ingredient to record.
     * @return this.
     */
    ModifiableBatchInfo addIngredient(IngredientArticle ingredient);
}
