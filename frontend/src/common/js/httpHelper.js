import axios from 'axios'
export const post = async (url, params) => await axios.request({
  url: url,
  method: 'POST',
  data: params
})
export const getByParams = async (url, params) => await axios.get(url, {
  params: {
    ...params
  }
})
