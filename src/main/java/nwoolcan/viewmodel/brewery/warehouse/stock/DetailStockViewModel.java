package nwoolcan.viewmodel.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.stock.BeerStock;
import nwoolcan.model.brewery.warehouse.stock.Stock;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}, without explicit records.
 */
public abstract class DetailStockViewModel extends MasterStockViewModel {

    private final List<RecordViewModel> records;
    private final Date creationDate;
    private final Date lastModified;
    /**
     * Constructor of the abstract view version of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param stock the {@link Stock} to be converted in {@link DetailStockViewModel}.
     * @param article related to the stock.
     */
    public DetailStockViewModel(final Stock stock, final Article article) {
        super(stock, article);
        this.records = stock.getRecords().stream().map(RecordViewModel::new).collect(Collectors.toList());
        this.creationDate = stock.getCreationDate();
        this.lastModified = stock.getLastChangeDate();
    }
    /**
     * Return the {@link List} of {@link RecordViewModel}.
     * @return the {@link List} of {@link RecordViewModel}.
     */
    public final List<RecordViewModel> getRecords() {
        return records;
    }
    /**
     * Generated a proper {@link DetailStockViewModel} from a general {@link Stock} accordingly with the {@link nwoolcan.model.brewery.warehouse.article.ArticleType} of the {@link nwoolcan.model.brewery.warehouse.article.Article} of the {@link Stock}.
     * @param stock to be converted.
     * @param article related to the stock.
     * @return the converted {@link DetailStockViewModel}.
     */
    public static DetailStockViewModel getDetailViewStock(final Stock stock, final Article article) {
        if (stock instanceof BeerStock) {
            return new BeerStockViewModel((BeerStock) stock, article);
        }
        return new PlainStockViewModel(stock, article);
    }
    /**
     * Return the string representation of the creation date.
     * @return the string representation of the creation date.
     */
    public final String getCreationDate() {
        return dateFormatted(creationDate);
    }

    /**
     * Return the string representation of the last modified date.
     * @return the string representation of the last modified date.
     */
    public final String getLastModified() {
        return dateFormatted(lastModified);
    }

}
