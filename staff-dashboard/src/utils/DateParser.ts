export function parseLocalDateTime(dateTimeObj: any): Date {
  const date = dateTimeObj.date;
  const time = dateTimeObj.time;

  return new Date(
    date.year,
    date.month - 1,
    date.day,
    time?.hour || 0,
    time?.minute || 0,
    time?.second || 0,
    Math.floor((time?.nano || 0) / 1_000_000)
  );
}

export function parseLocalDate(dateObj: any): Date {
  return new Date(dateObj.year, dateObj.month - 1, dateObj.day);
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
    date: {
      year: date.getFullYear(),
      month: date.getMonth() + 1,
      day: date.getDate(),
    },
    time: {
      hour: date.getHours(),
      minute: date.getMinutes(),
      second: date.getSeconds(),
      nano: date.getMilliseconds() * 1_000_000,
    },
  };
}
