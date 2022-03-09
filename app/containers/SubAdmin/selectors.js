import { createSelector } from 'reselect';
import { initialState } from './reducer';

/**
 * Direct selector to the subAdmin state domain
 */

const selectSubAdminDomain = state => state.subAdmin || initialState;

/**
 * Other specific selectors
 */

/**
 * Default selector used by SubAdmin
 */

const makeSelectSubAdmin = () =>
  createSelector(
    selectSubAdminDomain,
    substate => substate,
  );

export default makeSelectSubAdmin;
export { selectSubAdminDomain };
