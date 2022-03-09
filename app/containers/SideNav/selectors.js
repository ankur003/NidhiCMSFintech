import { createSelector } from 'reselect';
import { initialState } from './reducer';

/**
 * Direct selector to the sideNav state domain
 */

const selectSideNavDomain = state => state.sideNav || initialState;

/**
 * Other specific selectors
 */

/**
 * Default selector used by SideNav
 */

const makeSelectSideNav = () =>
  createSelector(
    selectSideNavDomain,
    substate => substate,
  );

export default makeSelectSideNav;
export { selectSideNavDomain };
