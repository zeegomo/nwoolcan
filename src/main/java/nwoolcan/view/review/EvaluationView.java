package nwoolcan.view.review;

import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.batch.review.EvaluationType;
import nwoolcan.utils.Results;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewType;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.viewmodel.brewery.production.batch.review.EvaluationViewModel;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

/**
 * Evaluation view. Display and/or accepts inputs for a specified evaluation category.
 */
@SuppressWarnings("NullAway")
public final class EvaluationView extends AbstractViewController implements InitializableController<EvaluationView.Builder> {
        private static final String DEFAULT_TEXT = "-fx-text-fill: black;";
        private static final String ERROR_TEXT = "-fx-text-fill: red;";
        @FXML
        private TextField scoreTextField;
        @FXML
        private Label scoreLabel;
        @FXML
        private Label maxScore;
        @FXML
        private TextArea notesTextArea;
        @FXML
        private Text notesText;
        @FXML
        private FlowPane notesFlowPane;
        @FXML
        private FlowPane scoreFlowPane;

        private final BooleanProperty validityProperty = new SimpleBooleanProperty(false);
        private boolean inputEnabled;

        /**
         * Creates itself and inject the controller and the view manager.
         *
         * @param controller  injected controller.
         * @param viewManager injected view manager.
         */
        public EvaluationView(final Controller controller, final ViewManager viewManager) {
            super(controller, viewManager);
        }

        /**
        * Returns a builder for this view.
        * @return  a builder for this view.
        */
        public static Builder builder() {
            return new Builder();
        }

        @Override
        public void initData(final EvaluationView.Builder builder) {
            this.inputEnabled = builder.inputEnabled;
            enableInput(this.inputEnabled);
            builder.data.ifPresent(this::displayData);
            builder.valid.ifPresent(valid -> valid.bind(this.validityProperty));
            builder.type.ifPresent(this::setInputCheck);
            builder.input.ifPresent(input -> {
                input.getLeft().bind(this.scoreTextProperty());
                input.getRight().bind(this.notesTextProperty());
            });
            builder.width.ifPresent(this::bindWidth);
            builder.type.ifPresent(type -> this.maxScore.setText(String.valueOf(type.getMaxScore())));
        }

        private void enableInput(final boolean input) {
            if (input) {
                this.notesFlowPane.getChildren().remove(this.notesText);
                this.scoreFlowPane.getChildren().remove(this.scoreLabel);
            } else {
                this.notesFlowPane.getChildren().remove(this.notesTextArea);
                this.scoreFlowPane.getChildren().remove(this.scoreTextField);
            }
        }

        private void setInputCheck(final EvaluationType type) {
            this.scoreTextField.focusedProperty().addListener((obv, unfocus, focus) -> {
                if (unfocus) {
                    Results.ofChecked(() -> Integer.parseInt(this.scoreTextField.getText()))
                           .flatMap(parsedScore ->
                               this.getController()
                                   .getBatchController()
                                   .checkEvaluation(type, parsedScore, Optional.of(this.notesTextArea.textProperty().get()))
                           )
                           .peek(good -> {
                               this.scoreTextField.setStyle(DEFAULT_TEXT);
                               this.validityProperty.setValue(true);
                           })
                           .peekError(bad -> {
                               this.scoreTextField.setStyle(ERROR_TEXT);
                               this.validityProperty.setValue(false);
                           });
                }
            });
        }

        private ReadOnlyStringProperty scoreTextProperty() {
            if (inputEnabled) {
                return this.scoreTextField.textProperty();
            } else {
                return this.scoreLabel.textProperty();
            }
        }

        private ReadOnlyStringProperty notesTextProperty() {
            if (inputEnabled) {
                return this.notesTextArea.textProperty();
            } else {
                return this.notesText.textProperty();
            }
        }

        private void bindWidth(final DoubleExpression width) {
            if (inputEnabled) {
                this.notesTextArea.maxWidthProperty().bind(width);
            } else {
                this.notesText.wrappingWidthProperty().bind(width);
            }
        }

        private void displayData(final EvaluationViewModel data) {
            if (inputEnabled) {
                data.getNotes().ifPresent(notes -> this.notesTextArea.setText(notes));
                this.scoreTextField.setText(String.valueOf(data.getScore()));
                this.scoreTextField.alignmentProperty().setValue(Pos.BASELINE_RIGHT);
            } else {
                data.getNotes().ifPresent(notes -> this.notesText.setText(notes));
                this.scoreLabel.setText(String.valueOf(data.getScore()));
                this.scoreLabel.alignmentProperty().setValue(Pos.BASELINE_RIGHT);
            }
            this.maxScore.setText(String.valueOf(data.getMaxScore()));
        }

    /**
     * Builder for Evaluation View.
     */
    public static class Builder {
        private Optional<DoubleExpression> width = Optional.empty();
        private boolean inputEnabled;
        private Optional<Pair<Property<String>, Property<String>>> input = Optional.empty();
        private Optional<BooleanProperty> valid = Optional.empty();
        private Optional<EvaluationViewModel> data = Optional.empty();
        private Optional<EvaluationType> type = Optional.empty();

        /**
         * Bind the width of this view to the provided {@link DoubleExpression}.
         * @param width the width to bind this view width to.
         * @return this.
         */
        public Builder bindWidth(final DoubleExpression width) {
            this.width = Optional.of(width);
            return this;
        }
        /**
         * Enabled input for the view.
         * @param enabled whether the view should be editable or not.
         * @return this.
         */
        public Builder enableInput(final boolean enabled) {
            this.inputEnabled = enabled;
            return this;
        }
        /**
         * Makes the view check for the validity of the input.
         * @param type the type of evaluation to check.
         * @return this.
         */
        public Builder checkInput(final EvaluationType type) {
            this.type = Optional.of(type);
            return this;
        }
        /**
         * Makes the view display the provided values.
         * @param data the values to display.
         * @return this.
         */
        public Builder displayValues(final EvaluationViewModel data) {
            this.data = Optional.of(data);
            return this;
        }
        /**
         * Binds the provided string properties to the input of this view.
         * @param input the properties to bind to the input of this view.
         * @return this.
         */
        public Builder bindWithInput(final Pair<Property<String>, Property<String>> input) {
            this.input = Optional.of(input);
            return this;
        }
        /**
         * Binds the provided boolean property to the validity of this view input.
         * @param valid the property to bind to the validity of this view input.
         * @return this.
         */
        public Builder bindWithInputValidity(final BooleanProperty valid) {
            this.valid = Optional.of(valid);
            return this;
        }
        /**
         * Builds the view.
         * @param manager the view manager used to retrieve fxml layout.
         * @return {@link Node}
         */
        public Node build(final ViewManager manager) {
            return manager.getView(ViewType.EVALUATION, this).orElse(new Label("Load failed"));
        }
    }
}
