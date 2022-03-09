import { createSelector } from 'reselect';
import { initialState } from './reducer';

/**
 * Direct selector to the pendingClient state domain
 */

const selectPendingClientDomain = state => state.pendingClient || initialState;

/**
 * Other specific selectors
 */

/**
 * Default selector used by PendingClient
 */

const makeSelectPendingClient = () =>
  createSelector(
    selectPendingClientDomain,
    substate => substate,
  );

export default makeSelectPendingClient;
export { selectPendingClientDomain };
