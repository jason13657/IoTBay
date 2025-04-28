type Prop = {
  title: string;
  message: string;
  onConfirm: () => void;
  onCancel: () => void;
};

export function ConfirmAlert({ title, message, onConfirm, onCancel }: Prop) {
  return (
    <div className="flex flex-col gap-5 shadow-md bg-white rounded-lg p-4 text-center">
      <h2 className="font-bold text-lg">{title}</h2>
      <p>{message}</p>
      <div className="flex justify-center gap-4">
        <button className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600" onClick={onConfirm}>
          Confirm
        </button>
        <button className="border border-blue-500 px-4 py-2 rounded hover:border-blue-500" onClick={onCancel}>
          Cancel
        </button>
      </div>
    </div>
  );
}
