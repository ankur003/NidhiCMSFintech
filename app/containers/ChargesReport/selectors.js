import { createSelector } from 'reselect';
import { initialState } from './reducer';

/**
 * Direct selector to the chargesReport state domain
 */

const selectChargesReportDomain = state => state.chargesReport || initialState;

/**
 * Other specific selectors
 */

/**
 * Default selector used by ChargesReport
 */

const makeSelectChargesReport = () =>
  createSelector(
    selectChargesReportDomain,
    substate => substate,
  );

export default makeSelectChargesReport;
export { selectChargesReportDomain };
