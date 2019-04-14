package nwoolcan.viewmodel.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.stock.BeerStock;
import nwoolcan.model.brewery.warehouse.stock.Stock;

import java.util.List;
import java.util.stream.Collectors;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}, without explicit records.
 */
public abstract class DetailStockViewModel extends MasterStockViewModel {

    private final List<RecordViewModel> records;
    /**
     * Constructor of the abstract view version of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param stock the {@link Stock} to be converted in {@link DetailStockViewModel}.
     */
    public DetailStockViewModel(final Stock stock) {
        super(stock);
        this.records = stock.getRecords().stream().map(RecordViewModel::new).collect(Collectors.toList());
    }
    /**
     * Return the {@link List} of {@link RecordViewModel}.
     * @return the {@link List} of {@link RecordViewModel}.
     */
    public final List<RecordViewModel> getRecords() {
        return records;
    }
    /**
     * Generated a proper {@link DetailStockViewModel} from a general {@link Stock} accordingly with the {@link ArticleType} of the {@link nwoolcan.model.brewery.warehouse.article.Article} of the {@link Stock}.
     * @param stock to be converted
     * @return the converted {@link DetailStockViewModel}.
     */
    public static DetailStockViewModel getDetailViewStock(final Stock stock) {
        if (stock.getArticle().getArticleType() == ArticleType.FINISHED_BEER) {
            return new BeerStockViewModel((BeerStock) stock);
        }
        return new PlainStockViewModel(stock);
    }
}
