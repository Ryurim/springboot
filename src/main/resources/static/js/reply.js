async function getReplyList({bbs_idx, page, page_size, goLast}){
    const result = await axios.get(`/board/replies/list/${bbs_idx}`, {params:{page, page_size}});
    if (goLast == "true") { // true 들어오면 재귀호출
        const total = result.data.total_count;
        // const lastPage = parseInt(Math.ceil(total/page_size));
        // console.log("****************************total : ", total, " lastPage : " , lastPage);
        return getReplyList({bbs_idx:bbs_idx, page:page, page_size:page_size})
    }
    return result.data;
}

async function replyDelete(idx) {
    const response = await axios.delete(`/board/replies/delete/${idx}`);
    return response.data;
}

