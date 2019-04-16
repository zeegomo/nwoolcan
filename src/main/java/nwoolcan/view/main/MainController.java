package nwoolcan.view.main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import nwoolcan.model.brewery.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.batch.review.BatchEvaluationBuilder;
import nwoolcan.model.brewery.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.batch.review.Evaluation;
import nwoolcan.model.brewery.batch.review.EvaluationFactory;
import nwoolcan.model.brewery.batch.review.types.BJCPBatchEvaluationType;
import nwoolcan.utils.Result;
import nwoolcan.controller.Controller;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDetailViewModel;

import java.util.ArrayList;
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
                                          .addNotes("Very goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery goodVery good")
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
        getController().setBreweryName("ciccio");
        getController().setOwnerName("ciccia");
        this.getViewManager().getView(ViewType.WAREHOUSE).peek(view -> this.contentPane.substitute(view));
    }

    /**
     * Shows the Production view.
     * @param event The occurred event
     */
    public void menuViewProductionClick(final ActionEvent event) {
        this.getViewManager().getView(ViewType.PRODUCTION, this.getController().getProductionViewModel()).peek(view -> this.contentPane.substitute(view));
    }
    /**
     * Shows the Warehouse view.
     * @param event The occurred event
     */
    public void menuViewEvaluationClick(final ActionEvent event) {
        this.getViewManager().getView(ViewType.BATCHEVALUATIONDETAIL, new BatchEvaluationDetailViewModel(bjcp)).peek(view -> this.contentPane.substitute(view));
    }
    /**
     * Shows the Articles view.
     * @param event The occurred event
     */
    public void menuViewArticlesClick(final ActionEvent event) {
        this.getViewManager()
            .getView(ViewType.ARTICLES)
            .peek(view -> this.contentPane.substitute(view));
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
