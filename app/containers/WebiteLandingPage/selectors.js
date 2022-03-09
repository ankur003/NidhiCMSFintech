import { createSelector } from 'reselect';
import { initialState } from './reducer';

/**
 * Direct selector to the webiteLandingPage state domain
 */

const selectWebiteLandingPageDomain = state =>
  state.webiteLandingPage || initialState;

/**
 * Other specific selectors
 */

/**
 * Default selector used by WebiteLandingPage
 */

const makeSelectWebiteLandingPage = () =>
  createSelector(
    selectWebiteLandingPageDomain,
    substate => substate,
  );

export default makeSelectWebiteLandingPage;
export { selectWebiteLandingPageDomain };
