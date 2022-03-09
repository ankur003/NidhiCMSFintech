import { createSelector } from 'reselect';
import { initialState } from './reducer';

/**
 * Direct selector to the accountStatement state domain
 */

const selectAccountStatementDomain = state =>
  state.accountStatement || initialState;

/**
 * Other specific selectors
 */

/**
 * Default selector used by AccountStatement
 */

const makeSelectAccountStatement = () =>
  createSelector(
    selectAccountStatementDomain,
    substate => substate,
  );

export default makeSelectAccountStatement;
export { selectAccountStatementDomain };
