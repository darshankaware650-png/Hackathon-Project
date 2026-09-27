const API_BASE = "https://your-service.onrender.com/api";

function getProfileId(){ return localStorage.getItem('profileId'); }
function setProfileId(id){ localStorage.setItem('profileId', id); }
function clearProfileId(){ localStorage.removeItem('profileId'); }

async function apiGet(path){
  const res=await fetch(API_BASE+path);
  if(!res.ok) throw new Error(`API ${res.status}: ${path}`);
  return res.json();
}
async function apiPost(path,body){
  const res=await fetch(API_BASE+path,{method:'POST',headers:{'Content-Type':'application/json'},body:body===undefined?undefined:JSON.stringify(body)});
  if(!res.ok) throw new Error(`API ${res.status}: ${path}`);
  return res.json();
}
async function apiDelete(path){
  const res=await fetch(API_BASE+path,{method:'DELETE'});
  if(!res.ok) throw new Error(`API ${res.status}: ${path}`);
  return res.json();
}
const $=id=>document.getElementById(id);
const safe=value=>String(value??'').replace(/[&<>"']/g,ch=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[ch]));
function toast(message){const el=$('toast');if(!el)return;el.textContent=message;el.classList.add('show');clearTimeout(toast.timer);toast.timer=setTimeout(()=>el.classList.remove('show'),2500);}

function initLogin(){
 const btn=$('continue-btn'); if(!btn)return;
 btn.addEventListener('click',()=>{
   if(getProfileId()) window.location.href='dashboard.html';
   else toast('No profile on this device yet. Create your profile to get started.');
 });
}
function initProfileForm(){
 const form=$('profile-form'); if(!form)return;
 if(getProfileId()){window.location.href='dashboard.html';return;}
 form.addEventListener('submit',async e=>{
  e.preventDefault();const error=$('form-error');if(error)error.textContent='';
  const name=$('name').value.trim(),education=$('education').value.trim();
  const skills=$('skills').value.split(',').map(s=>s.trim()).filter(Boolean);
  const interests=$('interests').value.split(',').map(s=>s.trim()).filter(Boolean);
  const preferredCategories=[...document.querySelectorAll('input[name="category"]:checked')].map(cb=>cb.value);
  const submit=form.querySelector('button[type="submit"]');submit.disabled=true;submit.textContent='Creating your profile…';
  try{const profile=await apiPost('/profile',{name,education,skills,interests,preferredCategories,bookmarks:[]});setProfileId(profile.id);window.location.href='dashboard.html';}
  catch(err){console.error(err);if(error)error.textContent='Could not create profile. Check that the backend is running, then try again.';submit.disabled=false;submit.innerHTML='Create profile <span>→</span>';}
 });
}

let allOpportunities=[],currentProfile=null,activeCategory='';
const sectionNames={overview:'Overview',explore:'Explore opportunities',recommendations:'For you',saved:'Saved',categories:'Categories',profile:'My profile',settings:'Settings'};
function showSection(name){
 document.querySelectorAll('.section-panel').forEach(el=>el.classList.toggle('active',el.id===`section-${name}`));
 document.querySelectorAll('.nav-item[data-section]').forEach(el=>el.classList.toggle('active',el.dataset.section===name));
 if($('breadcrumb-current'))$('breadcrumb-current').textContent=sectionNames[name]||'Overview';
 document.querySelector('.sidebar')?.classList.remove('open');
 if(name==='explore')applyFilters();
}
function initNavigation(){
 document.querySelectorAll('.nav-item[data-section]').forEach(btn=>btn.addEventListener('click',()=>showSection(btn.dataset.section)));
 document.querySelectorAll('[data-go]').forEach(btn=>btn.addEventListener('click',()=>showSection(btn.dataset.go)));
 document.querySelectorAll('[data-category]').forEach(btn=>btn.addEventListener('click',()=>{activeCategory=btn.dataset.category;showSection('explore');if($('category-filter'))$('category-filter').value=activeCategory;applyFilters();}));
 $('mobile-menu')?.addEventListener('click',()=>document.querySelector('.sidebar')?.classList.toggle('open'));
 $('logout-nav')?.addEventListener('click',()=>{clearProfileId();window.location.href='login.html';});
 $('reset-btn')?.addEventListener('click',()=>{if(confirm('Reset the profile saved on this browser?')){clearProfileId();window.location.href='index.html';}});
 $('category-filter')?.addEventListener('change',applyFilters);
 $('search-input')?.addEventListener('input',applyFilters);
}
function cardMarkup(o){
 const bookmarked=Boolean(currentProfile?.bookmarks?.includes(o.id));
 const tags=(o.tags||[]).slice(0,4).map(t=>`<span class="op-tag">${safe(t)}</span>`).join('');
 const validLink=o.link&&o.link!=='#';
 return `<article class="op-card"><div class="op-card-top"><span class="op-category" data-category="${safe(o.category)}">${safe(o.category||'Opportunity')}</span><button class="bookmark-btn ${bookmarked?'saved':''}" data-bookmark="${safe(o.id)}" aria-label="${bookmarked?'Remove bookmark':'Save opportunity'}">${bookmarked?'★':'♡'}</button></div><h3>${safe(o.title)}</h3><p>${safe(o.description||'Explore this opportunity and learn more about eligibility and how to apply.')}</p><div class="op-tags">${tags}</div><div class="op-card-foot"><span class="op-deadline">Deadline: <strong>${safe(o.deadline||'Check details')}</strong></span>${validLink?`<a class="view-link" href="${safe(o.link)}" target="_blank" rel="noopener noreferrer">View details ↗</a>`:'<span class="view-link unavailable">Details soon</span>'}</div></article>`;
}
function renderCards(target,items,emptyTitle,emptyText){
 const el=$(target);if(!el)return;
 if(!items?.length){el.innerHTML=`<div class="empty-state"><b>${safe(emptyTitle)}</b>${safe(emptyText)}</div>`;return;}
 el.innerHTML=items.map(cardMarkup).join('');
 el.querySelectorAll('[data-bookmark]').forEach(btn=>btn.addEventListener('click',()=>toggleBookmark(btn.dataset.bookmark)));
}
function getRecommended(){
 const keys=new Set([...(currentProfile?.skills||[]),...(currentProfile?.interests||[])].map(x=>x.toLowerCase().trim()));
 const prefs=new Set((currentProfile?.preferredCategories||[]).map(x=>x.toLowerCase()));
 return allOpportunities.map(o=>({...o,_score:(o.tags||[]).filter(t=>keys.has(t.toLowerCase())).length+(prefs.has((o.category||'').toLowerCase())?1:0)})).filter(o=>o._score>0).sort((a,b)=>b._score-a._score);
}
function renderDashboard(){
 const name=currentProfile?.name||'Student',initial=name.trim().charAt(0).toUpperCase()||'S';
 ['profile-name','profile-detail-name','side-name'].forEach(id=>{if($(id))$(id).textContent=name;});
 ['side-avatar','top-avatar','profile-avatar'].forEach(id=>{if($(id))$(id).textContent=initial;});
 if($('profile-detail-education'))$('profile-detail-education').textContent=currentProfile.education||'Education not added';
 if($('profile-skills'))$('profile-skills').textContent=(currentProfile.skills||[]).join(', ')||'Add skills to personalize your feed';
 if($('profile-interests'))$('profile-interests').textContent=(currentProfile.interests||[]).join(', ')||'Add interests to personalize your feed';
 if($('profile-detail-categories'))$('profile-detail-categories').textContent=(currentProfile.preferredCategories||[]).join(', ')||'No preferences selected';
 if($('stat-skills-count'))$('stat-skills-count').textContent=(currentProfile.skills||[]).length;
 if($('stat-interests-count'))$('stat-interests-count').textContent=(currentProfile.interests||[]).length;
 if($('stat-bookmarks-count'))$('stat-bookmarks-count').textContent=(currentProfile.bookmarks||[]).length;
 if($('saved-nav-count'))$('saved-nav-count').textContent=(currentProfile.bookmarks||[]).length;
 if($('saved-heading-count'))$('saved-heading-count').textContent=(currentProfile.bookmarks||[]).length;
 if($('stat-opportunities'))$('stat-opportunities').textContent=allOpportunities.length;
 const rec=getRecommended();
 renderCards('overview-recommended-grid',rec.slice(0,3),'Your picks are getting ready','Add skills or interests to your profile to get matched.');
 renderCards('recommended-grid',rec,'No matches yet','Try adding more skills or interests to your profile.');
 renderCards('opportunity-grid',allOpportunities,'No opportunities found','Try a different search or category.');
}
function applyFilters(){
 const category=$('category-filter')?.value||activeCategory;
 const query=($('search-input')?.value||'').toLowerCase().trim();
 const filtered=allOpportunities.filter(o=>(!category||o.category===category)&&(!query||[o.title,o.description,...(o.tags||[])].some(v=>String(v||'').toLowerCase().includes(query))));
 renderCards('opportunity-grid',filtered,'Nothing found','Try another keyword or select All categories.');
 if($('results-count'))$('results-count').textContent=`${filtered.length} ${filtered.length===1?'opportunity':'opportunities'} found`;
}
async function toggleBookmark(id){
 if(!currentProfile)return;
 const wasSaved=(currentProfile.bookmarks||[]).includes(id);
 const buttons=[...document.querySelectorAll(`[data-bookmark="${CSS.escape(id)}"]`)];buttons.forEach(b=>b.disabled=true);
 try{currentProfile=wasSaved?await apiDelete(`/profile/${currentProfile.id}/bookmark/${encodeURIComponent(id)}`):await apiPost(`/profile/${currentProfile.id}/bookmark/${encodeURIComponent(id)}`);
 renderDashboard();renderSaved();toast(wasSaved?'Removed from saved':'Opportunity saved');}
 catch(err){console.error(err);toast('Could not update bookmark. Try again.');}
 finally{document.querySelectorAll(`[data-bookmark="${CSS.escape(id)}"]`).forEach(b=>b.disabled=false);}
}
async function renderSaved(){
 if(!currentProfile)return;
 try{const saved=await apiGet(`/profile/${encodeURIComponent(currentProfile.id)}/bookmarks`);renderCards('bookmarks-grid',saved,'Nothing saved yet','Tap the bookmark icon on any opportunity to keep it here.');}
 catch(err){console.error(err);renderCards('bookmarks-grid',[],'Could not load saved items','Please refresh and try again.');}
}
async function initDashboard(){
 if(!$('section-overview'))return;initNavigation();
 const id=getProfileId();if(!id){window.location.href='index.html';return;}
 try{currentProfile=await apiGet('/profile/'+encodeURIComponent(id));allOpportunities=await apiGet('/opportunities');renderDashboard();await renderSaved();}
 catch(err){console.error(err);clearProfileId();alert('Your saved profile could not be loaded. Please create your profile again.');window.location.href='index.html';}
}
document.addEventListener('DOMContentLoaded',()=>{initLogin();initProfileForm();initDashboard();});
