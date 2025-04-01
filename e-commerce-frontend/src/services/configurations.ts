
import axiosInstance from "../utils/axiosInstance";

import { Configuration } from '@/types/configuration';

class ConfigurationService {
  async prepare(configuration: Omit<Configuration, 'id'>): Promise<Configuration> {
    const response= await axiosInstance.post<Configuration>('/configurations/prepare', configuration);
    return response;
  }

}

export default new ConfigurationService();
