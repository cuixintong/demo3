function delFruit(fid,pageno){
    if(confirm('是否确认删除？')){
        window.location.href='fruit.do?fid='+fid+'&pageno='+pageno+'&operate=delete';
    }
}

function page(pageno){
    window.location.href="fruit.do?pageno="+pageno;
}