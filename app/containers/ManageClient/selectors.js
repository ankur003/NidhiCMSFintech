import { createSelector } from 'reselect';
import { initialState } from './reducer';

/**
 * Direct selector to the manageClient state domain
 */

const selectManageClientDomain = state => state.manageClient || initialState;

/**
 * Other specific selectors
 */

/**
 * Default selector used by ManageClient
 */

const makeSelectManageClient = () =>
  createSelector(
    selectManageClientDomain,
    substate => substate,
  );

export default makeSelectManageClient;
export { selectManageClientDomain };
