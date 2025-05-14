import { AccessLog } from "../../models/AccessLog";
import { IAccessLogService } from "../AccessLogService";

export class AccessLogServiceStub implements IAccessLogService {
  private logs: AccessLog[] = [
    new AccessLog(1, 100, "Login", new Date("2025-04-23T12:00:00Z")),
    new AccessLog(2, 101, "Logout", new Date("2025-04-23T14:00:00Z")),
  ];

  async getLogs(): Promise<AccessLog[]> {
    return this.logs;
  }

  async getLogById(id: number): Promise<AccessLog> {
    const log = this.logs.find((l) => l.id === id);
    if (!log) {
      throw new Error(`Log with ID ${id} not found.`);
    }
    return log;
  }

  async getLogsByUserId(userId: number): Promise<AccessLog[]> {
    const filteredLogs = this.logs.filter((l) => l.userId == userId);
    if (filteredLogs.length === 0) {
      throw new Error(`No logs found for user with user id ${userId}.`);
    }
    return filteredLogs;
  }

  async createLog(log: AccessLog): Promise<AccessLog> {
    const newId = this.logs.length + 1;
    const newLog = new AccessLog(newId, log.userId, log.action, log.timestamp);
    this.logs.push(newLog);
    return newLog;
  }

  async deleteLog(id: number): Promise<void> {
    const index = this.logs.findIndex((l) => l.id === id);
    if (index === -1) {
      throw new Error(`Log with ID ${id} not found.`);
    }
    this.logs.splice(index, 1);
  }
}
