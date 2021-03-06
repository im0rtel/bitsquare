/*
 * This file is part of Bitsquare.
 *
 * Bitsquare is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bitsquare is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bitsquare. If not, see <http://www.gnu.org/licenses/>.
 */

package io.bitsquare.gui.main.portfolio.closed;

import io.bitsquare.gui.components.Popups;

import javax.inject.Inject;

import viewfx.view.FxmlView;
import viewfx.view.support.ActivatableViewAndModel;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

@FxmlView
public class ClosedTradesView extends ActivatableViewAndModel<GridPane, ClosedTradesViewModel> {

    @FXML TableView<ClosedTradesListItem> table;
    @FXML TableColumn<ClosedTradesListItem, ClosedTradesListItem> priceColumn, amountColumn, volumeColumn,
            directionColumn, dateColumn, tradeIdColumn;

    @Inject
    public ClosedTradesView(ClosedTradesViewModel model) {
        super(model);
    }

    @Override
    public void initialize() {
        setTradeIdColumnCellFactory();
        setDirectionColumnCellFactory();
        setAmountColumnCellFactory();
        setPriceColumnCellFactory();
        setVolumeColumnCellFactory();
        setDateColumnCellFactory();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No closed trades available"));
    }

    @Override
    public void doActivate() {
        table.setItems(model.getList());
    }

    private void openOfferDetails(ClosedTradesListItem item) {
        // TODO Open popup with details view
        log.debug("Trade details " + item);
        Popups.openWarningPopup("Under construction", "This will open a details " +
                "popup but that is not implemented yet.");
    }

    private void setTradeIdColumnCellFactory() {
        tradeIdColumn.setCellValueFactory((offerListItem) -> new ReadOnlyObjectWrapper<>(offerListItem.getValue()));
        tradeIdColumn.setCellFactory(
                new Callback<TableColumn<ClosedTradesListItem, ClosedTradesListItem>, TableCell<ClosedTradesListItem,
                        ClosedTradesListItem>>() {

                    @Override
                    public TableCell<ClosedTradesListItem, ClosedTradesListItem> call(TableColumn<ClosedTradesListItem,
                            ClosedTradesListItem> column) {
                        return new TableCell<ClosedTradesListItem, ClosedTradesListItem>() {
                            private Hyperlink hyperlink;

                            @Override
                            public void updateItem(final ClosedTradesListItem item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item != null && !empty) {
                                    hyperlink = new Hyperlink(model.getTradeId(item));
                                    hyperlink.setId("id-link");
                                    Tooltip.install(hyperlink, new Tooltip(model.getTradeId(item)));
                                    hyperlink.setOnAction(event -> openOfferDetails(item));
                                    setGraphic(hyperlink);
                                }
                                else {
                                    setGraphic(null);
                                    setId(null);
                                }
                            }
                        };
                    }
                });
    }

    private void setDateColumnCellFactory() {
        dateColumn.setCellValueFactory((offer) -> new ReadOnlyObjectWrapper<>(offer.getValue()));
        dateColumn.setCellFactory(
                new Callback<TableColumn<ClosedTradesListItem, ClosedTradesListItem>, TableCell<ClosedTradesListItem,
                        ClosedTradesListItem>>() {
                    @Override
                    public TableCell<ClosedTradesListItem, ClosedTradesListItem> call(
                            TableColumn<ClosedTradesListItem, ClosedTradesListItem> column) {
                        return new TableCell<ClosedTradesListItem, ClosedTradesListItem>() {
                            @Override
                            public void updateItem(final ClosedTradesListItem item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null)
                                    setText(model.getDate(item));
                                else
                                    setText("");
                            }
                        };
                    }
                });
    }


    private void setAmountColumnCellFactory() {
        amountColumn.setCellValueFactory((offer) -> new ReadOnlyObjectWrapper<>(offer.getValue()));
        amountColumn.setCellFactory(
                new Callback<TableColumn<ClosedTradesListItem, ClosedTradesListItem>, TableCell<ClosedTradesListItem,
                        ClosedTradesListItem>>() {
                    @Override
                    public TableCell<ClosedTradesListItem, ClosedTradesListItem> call(
                            TableColumn<ClosedTradesListItem, ClosedTradesListItem> column) {
                        return new TableCell<ClosedTradesListItem, ClosedTradesListItem>() {
                            @Override
                            public void updateItem(final ClosedTradesListItem item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(model.getAmount(item));
                            }
                        };
                    }
                });
    }

    private void setPriceColumnCellFactory() {
        priceColumn.setCellValueFactory((offer) -> new ReadOnlyObjectWrapper<>(offer.getValue()));
        priceColumn.setCellFactory(
                new Callback<TableColumn<ClosedTradesListItem, ClosedTradesListItem>, TableCell<ClosedTradesListItem,
                        ClosedTradesListItem>>() {
                    @Override
                    public TableCell<ClosedTradesListItem, ClosedTradesListItem> call(
                            TableColumn<ClosedTradesListItem, ClosedTradesListItem> column) {
                        return new TableCell<ClosedTradesListItem, ClosedTradesListItem>() {
                            @Override
                            public void updateItem(final ClosedTradesListItem item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(model.getPrice(item));
                            }
                        };
                    }
                });
    }

    private void setVolumeColumnCellFactory() {
        volumeColumn.setCellValueFactory((offer) -> new ReadOnlyObjectWrapper<>(offer.getValue()));
        volumeColumn.setCellFactory(
                new Callback<TableColumn<ClosedTradesListItem, ClosedTradesListItem>, TableCell<ClosedTradesListItem,
                        ClosedTradesListItem>>() {
                    @Override
                    public TableCell<ClosedTradesListItem, ClosedTradesListItem> call(
                            TableColumn<ClosedTradesListItem, ClosedTradesListItem> column) {
                        return new TableCell<ClosedTradesListItem, ClosedTradesListItem>() {
                            @Override
                            public void updateItem(final ClosedTradesListItem item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null)
                                    setText(model.getVolume(item));
                                else
                                    setText("");
                            }
                        };
                    }
                });
    }

    private void setDirectionColumnCellFactory() {
        directionColumn.setCellValueFactory((offer) -> new ReadOnlyObjectWrapper<>(offer.getValue()));
        directionColumn.setCellFactory(
                new Callback<TableColumn<ClosedTradesListItem, ClosedTradesListItem>, TableCell<ClosedTradesListItem,
                        ClosedTradesListItem>>() {
                    @Override
                    public TableCell<ClosedTradesListItem, ClosedTradesListItem> call(
                            TableColumn<ClosedTradesListItem, ClosedTradesListItem> column) {
                        return new TableCell<ClosedTradesListItem, ClosedTradesListItem>() {
                            @Override
                            public void updateItem(final ClosedTradesListItem item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(model.getDirectionLabel(item));
                            }
                        };
                    }
                });
    }
}

