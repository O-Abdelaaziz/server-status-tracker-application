import { Status } from './status';

export interface ServerResponse {
  id: number;
  uid: string;
  owner: string;
  ipAddress: string;
  name: string;
  memory: string;
  icon: string;
  type: string;
  status: Status;
  lastCheck: Date;
  lastCheckTimeAgo: string;
  createdAt: Date;
  createdAtTimeAgo: string;
  updatedAt: Date;
  updatedAtTimeAgo: string;
}
