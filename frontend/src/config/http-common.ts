import axios from "axios";
import config from './config';

export default axios.create({
  baseURL: `${config.server.url}/api/v1`,
  headers: {
    "Content-type": "application/json"
  }
});