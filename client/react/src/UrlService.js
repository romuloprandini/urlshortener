import { API_PATH, FULL_API_URL, } from './Constants';

export default {
  create: async (url) => {
    const rawResponse = await fetch(FULL_API_URL, 
      { method: 'POST', body: url, headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }});
      const shortedUrl = rawResponse.headers.get('Location');;
      return shortedUrl.replace(API_PATH, '');
  },
  get: async (url) => {
    const rawResponse = await fetch(`${url}?noRedirect=true}`, 
      { method: 'GET', headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }});
      const content = await rawResponse.json();
      return content;
  },
  topUrl: async () => {
    const rawResponse = await fetch(`${FULL_API_URL}/top?type=url`, 
      { method: 'GET', headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }});
      const content = await rawResponse.json();
      return content;
  }
}
