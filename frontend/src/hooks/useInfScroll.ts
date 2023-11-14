import useIntersect from "./useIntersect";

interface InfScrollProps {
  getMore: () => void;
  hasMore: boolean;
}

const useInfScroll = ({ getMore, hasMore }: InfScrollProps) => {
  const onIntersect = (entry: IntersectionObserverEntry, observer: IntersectionObserver) => {
    if (entry.isIntersecting && hasMore) {
      getMore();
      observer.disconnect();
    }
  };

  const options = {
    threshold: 0.6,
  }

  const { ref } = useIntersect(onIntersect, options);

  return { ref };
};

export default useInfScroll;