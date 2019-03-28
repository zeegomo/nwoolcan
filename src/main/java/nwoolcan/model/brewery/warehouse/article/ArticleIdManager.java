package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.utils.UnitOfMeasure;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.tuple.Triple;

/**
 * Id manager for {@link Article} objects. It is used by ArticleImpl to generate the
 * id and by WarehouseImpl to check the id of the elements.
 */
public final class ArticleIdManager {

    @Nullable private static ArticleIdManager instance;
    private int nextAvailableId;
    private Map<Triple<String, ArticleType, UnitOfMeasure>, Integer> existingIds;

    private ArticleIdManager() {
        nextAvailableId = 1;
        existingIds = new HashMap<>();
    }
    /**
     * Returns the only instance of the {@link ArticleIdManager} using a singleton pattern.
     * @return the only instance of the {@link ArticleIdManager} using a singleton pattern.
     */
    public static synchronized ArticleIdManager getInstance() {
        if (instance == null) {
            instance = new ArticleIdManager();
        }
        return instance;
    }
    /**
     * Getter of the id corresponding to the name, the type and the unit of measure of the
     * {@link Article}.
     * @param name of the new {@link Article}.
     * @param articleType of the new {@link Article}.
     * @param unitOfMeasure of the new {@link Article}.
     * @return the id corresponding to the name, the type and the unit of measure of the
     * {@link Article}.
     */
    // default so that only article impl can generate it.
    synchronized Integer getId(final String name,
                               final ArticleType articleType,
                               final UnitOfMeasure unitOfMeasure) {
        Triple<String, ArticleType, UnitOfMeasure> tuple = Triple.of(name, articleType, unitOfMeasure);
        if (!existingIds.containsKey(tuple)) {
            existingIds.put(tuple, nextAvailableId);
            nextAvailableId++;
        }
        return existingIds.get(tuple);
    }
    /**
     * Checks the consistency of the article.
     * @param article to be checked.
     * @return a boolean denoting whether the id is correct or not.
     */
    public synchronized boolean checkId(final Article article) {
        Triple<String, ArticleType, UnitOfMeasure> tuple = Triple.of(article.getName(),
                                                                     article.getArticleType(),
                                                                     article.getUnitOfMeasure());
        return existingIds.containsKey(tuple) && article.getId().equals(existingIds.get(tuple));
    }
}
