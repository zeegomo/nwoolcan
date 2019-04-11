package nwoolcan.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluationBuilder;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.production.batch.review.Evaluation;
import nwoolcan.model.brewery.production.batch.review.EvaluationFactory;
import nwoolcan.model.brewery.production.batch.review.types.BJCPBatchEvaluationType;
import nwoolcan.utils.Result;
import nwoolcan.controller.Controller;
import nwoolcan.controller.viewmodel.WarehouseViewModel;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationViewModel;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles the Main view.
 */
@SuppressWarnings("NullAway") // TODO
public final class MainController extends AbstractViewController {

    private final BatchEvaluationType bjcpType = BatchEvaluationBuilder.getAvailableBatchEvaluationTypes()
                                                                       .getValue()
                                                                       .stream()
                                                                       .filter(s -> s.getClass().equals(BJCPBatchEvaluationType.class))
                                                                       .findAny().get();

    Set<Evaluation> evals = Stream.<Result<Evaluation>>builder()
        .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.AROMA, 10))
        .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.APPEARANCE, 3))
        .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.FLAVOR, BJCPBatchEvaluationType.BJCPCategories.FLAVOR.getMaxScore()))
        .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.MOUTHFEEL, 4))
        .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.OVERALL_IMPRESSION, 10))
        .build()
        .filter(Result::isPresent)
        .map(Result::getValue)
        .collect(Collectors.toSet());

    BatchEvaluationBuilder builder = new BatchEvaluationBuilder();

    BatchEvaluation bjcp = builder.addReviewer("Andrea")
                                          .addNotes("Very good")
                                          .build(bjcpType, evals)
                                          .getValue();

    @FXML
    private SubViewContainer contentPane;

    /**
     * Creates itself and gets injected.
     * @param controller injected controller.
     * @param viewManager injected view manager.
     */
    public MainController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    /**
     * Shows the Dashboard.
     * @param event The occurred event
     */
    public void menuViewDashboardClick(final ActionEvent event) {
        this.getViewManager().getView(ViewType.DASHBOARD).peek(view -> this.contentPane.substitute(view));
    }

    /**
     * Shows the Warehouse view.
     * @param event The occurred event
     */
    public void menuViewWarehouseClick(final ActionEvent event) {
        this.getViewManager().getView(ViewType.WAREHOUSE, new WarehouseViewModel("ciccio")).peek(view -> this.contentPane.substitute(view));
    }
    /**
     * Shows the Warehouse view.
     * @param event The occurred event
     */
    public void menuViewEvaluationClick(final ActionEvent event) {
        ViewManager.getView(ViewType.BATCHEVALUATION, new BatchEvaluationViewModel(bjcp)).peek(view -> this.contentPane.substitute(view));
    }
    /**
     * Quits the application.
     * @param event The occurred event
     */
    public void menuFileQuitClick(final ActionEvent event) {
        Platform.exit();
    }

    /**
     * Print on the stdout the current number of overlays.
     * @param event The occurred event
     */
    public void menuFileCountOverlaysClick(final ActionEvent event) {
        System.out.println(contentPane.getOverlaysCount());
    }
}
