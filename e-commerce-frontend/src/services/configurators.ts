
import axiosInstance from "../utils/axiosInstance";

import { Configurator } from '@/types/product';

class ConfiguratorService {
  async create(configurator: Omit<Configurator, 'id'>): Promise<Configurator> {
    const { data } = await axiosInstance.post<Configurator>('/configurator', configurator);
    return data;
  }

}

export default new ConfiguratorService();
