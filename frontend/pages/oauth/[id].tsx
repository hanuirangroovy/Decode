import { useRouter } from "next/router";
import { useEffect } from "react";

export default function Authentication() {
  const router = useRouter();

  if (typeof window !== "undefined") {
    localStorage.setItem("token", String(router.query.token));
  }

  useEffect(() => {
    if (localStorage.token !== "undefined"){
      // location.href = "http://localhost:3000/"
      // location.href = "http://j6c203.p.ssafy.io:3000/"
      location.href = "https://j6c203.p.ssafy.io/"
    }
  }, );

  return <></>;
}
