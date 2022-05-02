const IsLogin = () => {
  if (typeof window !== "undefined") {
    const token = localStorage.getItem("token");
  
    if (!token || token == "undefined") {
      return false;
    } else return true;
  }

}
export default IsLogin;
