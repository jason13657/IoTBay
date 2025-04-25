export function parseLocalDate(dateObj: any): Date {
  return new Date(dateObj.year, dateObj.month - 1, dateObj.day);
}

export function parseLocalDateTime(dateObj: any): Date {
  return new Date(
    dateObj.year,
    dateObj.month - 1,
    dateObj.day,
    dateObj.hour || 0,
    dateObj.minute || 0,
    dateObj.second || 0
  );
}

export function toLocalDateJson(date: Date): any {
  return {
    year: date.getFullYear(),
    month: date.getMonth() + 1,
    day: date.getDate(),
  };
}

export function toLocalDateTimeJson(date: Date): any {
  return {
    year: date.getFullYear(),
    month: date.getMonth() + 1,
    day: date.getDate(),
    hour: date.getHours(),
    minute: date.getMinutes(),
    second: date.getSeconds(),
  };
}
