import RequestUtils from '@/components/Dingkc/Common/RequestUtils';

export async function getFileList(params) {
  return RequestUtils.requestGET(`/ftpFiles`,params);
}

export async function deleteFile(params) {
  return RequestUtils.requestDELETE(`/ftpFiles/${params.ftpFileId}`,null);
}

export async function downloadFile(params) {
  return RequestUtils.requestPUT(`/ftpFiles/downloads`,null,params);
}

export async function moveFile(params) {
  return RequestUtils.requestPUT(`/ftpFiles/${params.ftpFileId}`,null,params);
}

export async function uploadFile(params) {
  return RequestUtils.requestPUT(`/ftpFiles/uploads`,null,params);
}
