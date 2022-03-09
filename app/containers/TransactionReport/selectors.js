import { createSelector } from 'reselect';
import { initialState } from './reducer';

/**
 * Direct selector to the transactionReport state domain
 */

const selectTransactionReportDomain = state =>
  state.transactionReport || initialState;

/**
 * Other specific selectors
 */

/**
 * Default selector used by TransactionReport
 */

const makeSelectTransactionReport = () =>
  createSelector(
    selectTransactionReportDomain,
    substate => substate,
  );

export default makeSelectTransactionReport;
export { selectTransactionReportDomain };
