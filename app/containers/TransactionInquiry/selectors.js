import { createSelector } from 'reselect';
import { initialState } from './reducer';

/**
 * Direct selector to the transactionInquiry state domain
 */

const selectTransactionInquiryDomain = state =>
  state.transactionInquiry || initialState;

/**
 * Other specific selectors
 */

/**
 * Default selector used by TransactionInquiry
 */

const makeSelectTransactionInquiry = () =>
  createSelector(
    selectTransactionInquiryDomain,
    substate => substate,
  );

export default makeSelectTransactionInquiry;
export { selectTransactionInquiryDomain };
