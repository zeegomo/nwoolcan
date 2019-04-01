package nwoolcan.model.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.utils.Quantities;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import org.apache.commons.lang3.time.DateUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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

    private static final int EMPTY_VALUE = 0;

    private final Article article;
    @Nullable private final Date expirationDate;
    private final List<Record> records = new ArrayList<>();
    private final Date creationDate;
    private Date lastChangeDate;
    private Quantity remainingQuantity;
    private Quantity usedQuantity;

    /**
     * Constructor for Stock with expiration date.
     * @param article referred to the Stock.
     * @param expirationDate of the Stock. It can be null, which means there is no expiration date.
     */
    public StockImpl(final Article article, @Nullable final Date expirationDate) {
        Date creationMoment = new Date();
        this.article = article;
        this.expirationDate = expirationDate == null ? null : DateUtils.round(expirationDate, Calendar.DATE);
        this.creationDate = creationMoment;
        this.lastChangeDate = creationMoment;
        this.remainingQuantity = Quantity.of(EMPTY_VALUE, article.getUnitOfMeasure());
        this.usedQuantity = Quantity.of(EMPTY_VALUE, article.getUnitOfMeasure());
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
        if (this.remainingQuantity.getValue() > 0) {
            if (this.expirationDate != null && this.expirationDate.before(new Date())) {
                return StockState.EXPIRED;
            }
            return StockState.AVAILABLE;
        }
        return StockState.USED_UP;
    }

    @Override
    public Optional<Date> getExpirationDate() {
        final Date d = expirationDate == null ? null : (Date) expirationDate.clone();
        return Optional.ofNullable(d);
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
    public Result<Empty> addRecord(final Record record) {
        final Result<Quantity> res;
        if (record.getAction().equals(Record.Action.ADDING)) {
            // adding the quantity of the record to the temporary current remaining quantity.
            res = Quantities.add(this.remainingQuantity, record.getQuantity());
        } else { //REMOVING
            // remove the quantity of the record from the temporary current quantity and add it
            // to the used quantity.
            res = Quantities.remove(this.remainingQuantity, record.getQuantity())
                            .peek(q ->
                                this.usedQuantity = Quantities.add(this.usedQuantity,
                                                                   record.getQuantity())
                                                                         .getValue());
        }
        // make the temporary quantity the official one, add the record to the records list
        // and return a Result of Empty.
        return res.peek(q -> this.remainingQuantity = q)
                  .peek(q -> {
                      records.add(record);
                  })
                  .peek(q -> this.updateLastChangeDate())
                  .toEmpty();
    }

    @Override
    public List<Record> getRecords() {
        return Collections.unmodifiableList(
            new ArrayList<>(this.records).stream()
                                         .sorted(Comparator.comparing(Record::getDate))
                                         .collect(Collectors.toList()));
    }

    private void updateLastChangeDate() {
        this.lastChangeDate = new Date();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.article, this.expirationDate);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof StockImpl)) {
            return false;
        }

        StockImpl other = (StockImpl) obj;
        return this.article.equals(other.getArticle())
            && Optional.ofNullable(this.expirationDate).equals(other.getExpirationDate());
    }


    @Override
    public String toString() {
        return "[StockImpl]{"
            + "article=" + article
            + ", expirationDate=" + expirationDate
            + ", records=" + records
            + ", creationDate=" + creationDate
            + ", lastChangeDate=" + lastChangeDate
            + ", remainingQuantity=" + remainingQuantity
            + ", usedQuantity=" + usedQuantity
            + '}';
    }
}
