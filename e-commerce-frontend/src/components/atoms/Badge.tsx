interface BadgeProps {
    count: number;
    className?: string;
  }
  
  export const Badge = ({ count, className = "" }: BadgeProps) => {
    if (count <= 0) return null;
  
    return (
      <span
        className={`absolute -top-2 -right-2 bg-stone-500 text-white text-[10px] w-4 h-4 flex items-center justify-center rounded-full ${className}`}
      >
        {count}
      </span>
    );
  };
  