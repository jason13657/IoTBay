export class AccessLog {
  id: number;
  userId: number;
  action: string;
  timestamp: Date;

  constructor(id: number, userId: number, action: string, timestamp: Date) {
    this.id = id;
    this.userId = userId;
    this.action = action;
    this.timestamp = timestamp;
  }

  static fromJson(json: any): AccessLog {
    return new AccessLog(json.id, json.userId, json.action, new Date(json.timestamp));
  }

  toJson(): Record<string, any> {
    return {
      id: this.id,
      userId: this.userId,
      action: this.action,
      timestamp: this.timestamp.toISOString(),
    };
  }
}
