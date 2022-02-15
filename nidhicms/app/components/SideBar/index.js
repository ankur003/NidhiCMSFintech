/**
 *
 * SideBar
 *
 */

import React, { memo } from 'react';
// import PropTypes from 'prop-types';
// import styled from 'styled-components';

import { FormattedMessage } from 'react-intl';
import messages from './messages';

function SideBar() {
  return (
    <div>
      <FormattedMessage {...messages.header} />
    </div>
  );
}

SideBar.propTypes = {};

export default memo(SideBar);
