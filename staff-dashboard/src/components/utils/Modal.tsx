import { ReactNode } from "react";
import { Portal } from "./Portal";

type Props = {
  children: ReactNode;
  onClose: () => void;
};

export function Modal({ children, onClose }: Props) {
  const handleBackgroundClick = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  return (
    <Portal>
      <div
        onClick={handleBackgroundClick}
        className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50"
      >
        {children}
      </div>
    </Portal>
  );
}
