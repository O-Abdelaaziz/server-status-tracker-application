import { ServerResponse } from './server-response';

export interface CustomResponse {
  timeStamp: Date;
  statusCode: number;
  status: string;
  reason: string;
  message: string;
  developerMessage: string;
  data: { servers?: ServerResponse[]; server?: ServerResponse };
}
