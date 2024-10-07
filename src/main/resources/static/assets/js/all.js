const includeJs = (path) => {
    const js = document.createElement("script");
    js.async = true;
    js.src = path;

    document.getElementsByTagName("head")[0].appendChild(js);
}

includeJs("/assets/js/theme-switcher.js");
includeJs("/assets/js/modal.js");
