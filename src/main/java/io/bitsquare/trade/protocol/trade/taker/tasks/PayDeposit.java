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

package io.bitsquare.trade.protocol.trade.taker.tasks;

import io.bitsquare.btc.WalletService;
import io.bitsquare.util.task.ExceptionHandler;

import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PayDeposit {
    private static final Logger log = LoggerFactory.getLogger(PayDeposit.class);

    public static void run(ResultHandler resultHandler,
                           ExceptionHandler exceptionHandler,
                           WalletService walletService,
                           Coin securityDeposit,
                           Coin tradeAmount,
                           String tradeId,
                           String pubKeyForThatTrade,
                           String arbitratorPubKey,
                           String offererPubKey,
                           String preparedOffererDepositTxAsHex) {
        log.trace("Run task");
        try {
            Coin amountToPay = tradeAmount.add(securityDeposit);
            Coin msOutputAmount = amountToPay.add(securityDeposit);

            Transaction signedTakerDepositTx = walletService.takerAddPaymentAndSignTx(amountToPay,
                    msOutputAmount,
                    offererPubKey,
                    pubKeyForThatTrade,
                    arbitratorPubKey,
                    preparedOffererDepositTxAsHex,
                    tradeId);

            log.trace("sharedModel.signedTakerDepositTx: " + signedTakerDepositTx);
            resultHandler.onResult(signedTakerDepositTx);
        } catch (InsufficientMoneyException e) {
            log.error("Pay deposit faultHandler.onFault due InsufficientMoneyException " + e);
            exceptionHandler.handleException(
                    new Exception("Pay deposit faultHandler.onFault due InsufficientMoneyException " + e));
        }
    }

    public interface ResultHandler {
        void onResult(Transaction signedTakerDepositTx);
    }


}
