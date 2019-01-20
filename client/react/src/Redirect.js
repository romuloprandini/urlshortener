import React from 'react';

import { FULL_API_URL } from './Constants'


const redirect = ({url}) => {
  window.location = `${FULL_API_URL}/${url}`;
  return (
    <section>Redirecting...</section>
  )
}

export default redirect;
