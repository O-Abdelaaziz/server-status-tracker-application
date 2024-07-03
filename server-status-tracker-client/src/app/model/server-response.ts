import { Status } from './status';

export interface ServerResponse {
  id: number;
  uid: string;
  name: string;
  owner: string;
  ipAddress: string;
  memory: string;
  type: string;
  icon: string;
  status: Status;
  lastCheck: Date;
  lastCheckTimeAgo: string;
  createdAt: Date;
  createdAtTimeAgo: string;
  updatedAt: Date;
  updatedAtTimeAgo: string;
}
