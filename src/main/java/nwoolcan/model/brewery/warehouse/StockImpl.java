package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.utils.Quantities;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 *
 */
public final class StockImpl implements Stock {

    private final Integer id;
    private final Article article;
    private final Date expirationDate;
    private final List<Record> records = new ArrayList<>();
    private final Date creationDate;
    private Date lastChangeDate;
    private Quantity remainingQuantity;
    private Quantity usedQuantity;

    /**
     * Constructor for Stock with expiration date.
     * @param id of the Stock.
     * @param article referred to the Stock.
     * @param expirationDate of the Stock. It can be null, which means there is no expiration date,
     *                       but if this is the case use the other constructor.
     */
    // Package protected
    StockImpl(final Integer id, final Article article, final Date expirationDate) {
        this.id = Objects.requireNonNull(id);
        this.article = Objects.requireNonNull(article);
        this.expirationDate = expirationDate; // not required to be nonNull because it is called by
                                              // the other constructor.
        this.creationDate = new Date();
        this.lastChangeDate = new Date();
    }
    /**
     * Constructor for Stock without expiration date.
     * @param id of the Stock.
     * @param article referred to the Stock.
     */
    // Package protected
    StockImpl(final Integer id, final Article article) {
        this(id, article, null);
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public Article getArticle() {
        return this.article;
    }

    @Override
    public Quantity getRemainingQuantity() {
        return this.remainingQuantity;
    }

    @Override
    public Quantity getUsedQuantity() {
        return this.usedQuantity;
    }

    @Override
    public StockState getState() {
        return null;
    }

    @Override
    public Optional<Date> getExpirationDate() {
        return Optional.ofNullable((Date) this.expirationDate.clone());
    }

    @Override
    public Date getCreationDate() {
        return (Date) this.creationDate.clone();
    }

    @Override
    public Date getLastChangeDate() {
        return (Date) this.lastChangeDate.clone();
    }

    @Override
    public Result<Empty> addRecord(final Record record) { // TODO
        final Result<Quantity> res;
        if (record.getAction().equals(Record.Action.ADDING)) {
            res = Quantities.add(this.remainingQuantity, record.getQuantity());
        } else {
            res = Quantities.remove(this.remainingQuantity, record.getQuantity());
        }
        return null;
    }

    @Override
    public List<Record> getRecords() {
        return Collections.unmodifiableList(
            new ArrayList<Record>(this.records).stream()
                .sorted((a, b) -> (a.getDate().compareTo(b.getDate())))
                .collect(Collectors.toList()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.article, this.expirationDate); // TODO check if it is logically correct
    }

    @Override
    public boolean equals(final Object obj) { // TODO check if it logically correct
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof StockImpl)) {
            return false;
        }

        StockImpl other = (StockImpl) obj;
        return this.id.equals(other.getId())
            && this.article.equals(other.getArticle())
            && Optional.ofNullable(this.expirationDate).equals(other.getExpirationDate());
    }


    @Override
    public String toString() {
        return "StockImpl{"
            + "id=" + id
            + ", article=" + article
            + ", expirationDate=" + expirationDate
            + ", records=" + records
            + ", creationDate=" + creationDate
            + ", lastChangeDate=" + lastChangeDate
            + ", remainingQuantity=" + remainingQuantity
            + ", usedQuantity=" + usedQuantity
            + '}';
    }
}