import { DateState } from './data-state';

export interface AppState<T> {
  dataState: DateState;
  appData?: T;
  error?: string;
}
