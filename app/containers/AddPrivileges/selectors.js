import { createSelector } from 'reselect';
import { initialState } from './reducer';

/**
 * Direct selector to the addPrivileges state domain
 */

const selectAddPrivilegesDomain = state => state.addPrivileges || initialState;

/**
 * Other specific selectors
 */

/**
 * Default selector used by AddPrivileges
 */

const makeSelectAddPrivileges = () =>
  createSelector(
    selectAddPrivilegesDomain,
    substate => substate,
  );

export default makeSelectAddPrivileges;
export { selectAddPrivilegesDomain };
