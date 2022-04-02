import { createSelector } from 'reselect';
import { initialState } from './reducer';

/**
 * Direct selector to the userVerification state domain
 */

const selectUserVerificationDomain = state =>
  state.userVerification || initialState;

/**
 * Other specific selectors
 */

/**
 * Default selector used by UserVerification
 */

const makeSelectUserVerification = () =>
  createSelector(
    selectUserVerificationDomain,
    substate => substate,
  );

export default makeSelectUserVerification;
export { selectUserVerificationDomain };
