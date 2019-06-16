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

export async function addFile(params) {
  return RequestUtils.requestPOST(`/ftpFiles/dirs`,params);
}

export async function getFileDetail(params) {
  return RequestUtils.requestGET(`/ftpFiles/${params.ftpFileId}/previews`,params);
}

export async function getRecycleList(params) {
  return RequestUtils.requestGET(`/recycle-bins`,params);
}

export async function deleteRecycle(params) {
  return RequestUtils.requestDELETE(`/recycle-bins/${params.recycleBinId}`,null);
}

export async function reductionRecycle(params) {
  return RequestUtils.requestPUT(`/recycle-bins/${params.recycleBinId}/returns`,null,params)
}

export async function cleanAllRecycle() {
  return RequestUtils.requestDELETE(`/recycle-bins-all`,null);
}
