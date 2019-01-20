const apiUrl = "http://localhost:8080";

export default {
  create: async (url) => {
    const rawResponse = await fetch(apiUrl, 
      { method: 'POST', body: url, headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }});
      const shortedUrl = rawResponse.headers.get('Location');;
      return shortedUrl;
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
    const rawResponse = await fetch(`${apiUrl}/top?type=url`, 
      { method: 'GET', headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }});
      const content = await rawResponse.json();
      return content;
  }
}
