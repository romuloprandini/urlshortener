import React from 'react';

import { API_URL } from './Constants'


const redirect = ({url}) => {
  window.location = `${API_URL}/${url}`;
  return (
    <section>Redirecting...</section>
  )
}

export default redirect;
