import RequestUtils from '@/components/Dingkc/Common/RequestUtils';

export async function getFriendsList(params) {
  return RequestUtils.requestGET(`/friends`,params);
}
