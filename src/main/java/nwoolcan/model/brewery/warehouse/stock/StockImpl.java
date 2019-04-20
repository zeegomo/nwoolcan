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
public class StockImpl implements Stock {

    private static final int EMPTY_VALUE = 0;

    private final int articleId;
    @Nullable private final Date expirationDate;
    private final List<Record> records = new ArrayList<>();
    private final Date creationDate;
    private Date lastChangeDate;
    private Quantity remainingQuantity;
    private Quantity usedQuantity;
    private final int id;

    /**
     * Constructor for Stock with expiration date.
     * @param article referred to the Stock.
     * @param expirationDate of the Stock. It can be null, which means there is no expiration date.
     */
    // Package-Private
    StockImpl(final int id, final Article article, @Nullable final Date expirationDate) {
        Date creationMoment = new Date();
        this.id = id;
        this.articleId = article.getId();
        this.expirationDate = expirationDate == null ? null : DateUtils.truncate(expirationDate, Calendar.DATE);
        this.creationDate = creationMoment;
        this.lastChangeDate = creationMoment;
        this.remainingQuantity = Quantity.of(EMPTY_VALUE, article.getUnitOfMeasure()).getValue();
        this.usedQuantity = Quantity.of(EMPTY_VALUE, article.getUnitOfMeasure()).getValue();
    }

    @Override
    public final int getId() {
        return id;
    }

    @Override
    public final int getArticleId() {
        return this.articleId;
    }

    @Override
    public final Quantity getRemainingQuantity() {
        return this.remainingQuantity;
    }

    @Override
    public final Quantity getUsedQuantity() {
        return this.usedQuantity;
    }

    @Override
    public final StockState getState() {
        if (this.remainingQuantity.getValue() > 0) {
            if (this.expirationDate != null && this.expirationDate.before(DateUtils.truncate(new Date(), Calendar.DATE))) {
                return StockState.EXPIRED;
            }
            return StockState.AVAILABLE;
        }
        return StockState.USED_UP;
    }

    @Override
    public final Optional<Date> getExpirationDate() {
        final Date d = expirationDate == null ? null : (Date) expirationDate.clone();
        return Optional.ofNullable(d);
    }

    @Override
    public final Date getCreationDate() {
        return (Date) this.creationDate.clone();
    }

    @Override
    public final Date getLastChangeDate() {
        return (Date) this.lastChangeDate.clone();
    }

    @Override
    public final Result<Empty> addRecord(final Record record) {

        if (!records.isEmpty() && record.getDate().before(records.get(records.size() - 1).getDate())) {
            return Result.error(new IllegalArgumentException("You can not add a record which date is previous than the"
                                                           + "last one."));
        }

        final Result<Quantity> res;
        if (record.getAction().equals(Record.Action.ADDING)) {
            // adding the quantity of the record to the temporary current remaining quantity.
            res = Quantities.add(this.remainingQuantity, record.getQuantity());
        } else { //REMOVING
            // remove the quantity of the record from the temporary current quantity and add it
            // to the used quantity. It fails when quantity gets negative.
            res = Quantities.remove(this.remainingQuantity, record.getQuantity())
                            .peek(q ->
                                this.usedQuantity = Quantities.add(this.usedQuantity,
                                                                   record.getQuantity())
                                                                         .getValue())
                            .mapError(e -> new IllegalArgumentException("You can not have a stock with negative"
                                                                      + "available amount."));
        }
        // make the temporary quantity the official one, add the record to the records list
        // and return a Result of Empty.
        return res.peek(q -> this.remainingQuantity = q)
                  .peek(q -> records.add(record))
                  .peek(q -> this.updateLastChangeDate())
                  .toEmpty();
    }

    @Override
    public final List<Record> getRecords() {
        return Collections.unmodifiableList(
            new ArrayList<>(this.records).stream()
                                         .sorted(Comparator.comparing(Record::getDate))
                                         .collect(Collectors.toList()));
    }
    /**
     * Update the lastChangeDate.
     */
    private void updateLastChangeDate() {
        this.lastChangeDate = new Date();
    }
    /**
     * Return the hash code of the class. To override this method call hash this hash and eventual
     * other parameters. Do not include id in hash.
     * @return the hash code of the class.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.articleId, this.expirationDate);
    }
    /**
     * Return a boolean denoting whether the {@link Stock} is the same. To override this method
     * call compare eventual other element and call super() on this.
     * @return the hash code of the class.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Stock)) {
            return false;
        }

        Stock other = (Stock) obj;
        return this.articleId == other.getArticleId()
            && Optional.ofNullable(this.expirationDate).equals(other.getExpirationDate());
    }
    /**
     * Returns a string representation of the class. To extend it insert also eventual other parameters.
     * @return a string representation of the class.
     */
    @Override
    public String toString() {
        return "[StockImpl]{"
            + "articleId=" + articleId
            + ", expirationDate=" + (getExpirationDate().isPresent() ? getExpirationDate().get() : "null")
            + ", records=" + records
            + ", creationDate=" + creationDate
            + ", lastChangeDate=" + lastChangeDate
            + ", remainingQuantity=" + remainingQuantity
            + ", usedQuantity=" + usedQuantity
            + '}';
    }
}
